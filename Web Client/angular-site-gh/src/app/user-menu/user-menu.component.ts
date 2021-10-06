import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {DataService} from '../services/data.service';
import {HttpClient} from '@angular/common/http';
import {MatDialog, MatPaginator, MatSort} from '@angular/material';
import {Issue} from '../models/issue';
import {Observable} from 'rxjs/Observable';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';
import {DataSource} from '@angular/cdk/collections';
import 'rxjs/add/observable/merge';
import 'rxjs/add/observable/fromEvent';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';
import {AddDialogComponent} from '../dialogs/add/add.dialog.component';
import {EditDialogComponent} from '../dialogs/edit/edit.dialog.component';
import {DeleteDialogComponent} from '../dialogs/delete/delete.dialog.component';
import { environment} from '../../environments/environment';

@Component({
  selector: 'app-user-menu',
  templateUrl: './user-menu.component.html',
  styleUrls: ['./user-menu.component.css']
})
export class UserMenuComponent implements OnInit {
  primary = 'red';

  //private API_URL = 'http://gajoku-env-1.remcewpict.ap-northeast-2.elasticbeanstalk.com/editmenu?r_admin=thejinkuk&id=15';
  private lo_API_URL = `${environment.apiBaseUrl}`;
  displayedColumns = ['menu', 'cost', 'actions'];
  exampleDatabase: DataService | null;
  dataSource: ExampleDataSource | null;
  index: number;
  //id: number;

  constructor(public httpClient: HttpClient,
              public dialog: MatDialog,
              public dataService: DataService) {}

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild('filter') filter: ElementRef;

  ngOnInit() {
    this.loadData();
  }

  refresh() {
    this.loadData();
  }

  addNew(issue: Issue) {
    //let obj: Issue[];
    const dialogRef = this.dialog.open(AddDialogComponent, {
      data: {issue: issue }
    });

    dialogRef.afterClosed().subscribe(result => {

      if (result === 1) {
        // After dialog is closed we're doing frontend updates
        // For add we're just pushing a new row inside DataService
        this.exampleDatabase.dataChange.value.push(this.dataService.getDialogData());
        //let obj = this.exampleDatabase.dataChange.value;
        let admin = localStorage.getItem('token');
        let menu = this.exampleDatabase.dataChange.value;
        console.log('in addnew this.exampleDatabase.dataChange.value is ', this.exampleDatabase.dataChange.value);
        console.log('in addnew getDialogdata() is ', this.dataService.getDialogData());
        let sendbody = {
          r_admin: admin,
          r_menu: JSON.stringify(menu)
        }
        //JSON.stringify([r_menu]);
        this.httpClient.post(`${this.lo_API_URL}/editmenu`, sendbody, {responseType : 'text'}).subscribe(data =>{
          console.log(data);
          console.log('data to DataBase');
          localStorage.removeItem('dialogdatacost');
          localStorage.removeItem('dialogdatamenu');
        }),
          this.refreshTable();
      }
    });
  }

  startEdit(i: number, menu: string, cost: string) {
    //this.id = id;
    // index row is used just for debugging proposes and can be removed
    this.index = i;
    console.log(this.index);
    const dialogRef = this.dialog.open(EditDialogComponent, {
      data: {menu: menu, cost: cost}
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === 1) {
        // When using an edit things are little different, firstly we find record inside DataService by id
        //const foundIndex = this.exampleDatabase.dataChange.value.findIndex(x => x.id === this.id);
        // Then you update that record using data from dialogData (values you enetered)
        this.exampleDatabase.dataChange.value[this.index] = this.dataService.getDialogData();
        // And lastly refresh table
        let admin = localStorage.getItem('token');
        let menu = this.exampleDatabase.dataChange.value;
        let sendbody = {
          r_admin: admin,
          r_menu: JSON.stringify(menu)
        }
        //JSON.stringify([r_menu]);
        this.httpClient.post(`${this.lo_API_URL}/editmenu`, sendbody, {responseType : 'text'}).subscribe(data =>{
          console.log(data);
          console.log('data to DataBase');
          localStorage.removeItem('dialogdatacost');
          localStorage.removeItem('dialogdatamenu');
        }),
          this.refreshTable();
      }
    });
  }

  deleteItem(i: number, menu: string, cost: string) {
    this.index = i;
    //this.id = id;
    const dialogRef = this.dialog.open(DeleteDialogComponent, {
      data: {menu: menu, cost: cost}
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === 1) {
        //const foundIndex = this.exampleDatabase.dataChange.value.findIndex(x => x.id === this.id);
        // for delete we use splice in order to remove single object from DataService
        this.exampleDatabase.dataChange.value.splice(this.index, 1);
        let admin = localStorage.getItem('token');
        let menu = this.exampleDatabase.dataChange.value;
        let sendbody = {
          r_admin: admin,
          r_menu: JSON.stringify(menu)
        }
        //JSON.stringify([r_menu]);
        this.httpClient.post(`${this.lo_API_URL}/editmenu`, sendbody, {responseType : 'text'}).subscribe(data =>{
          console.log(data);
          console.log('data to DataBase');
          localStorage.removeItem('dialogdatacost');
          localStorage.removeItem('dialogdatamenu');
        }),
          this.refreshTable();
      }
    });
  }


  // If you don't need a filter or a pagination this can be simplified, you just use code from else block
  private refreshTable() {
    // if there's a paginator active we're using it for refresh
    if (this.dataSource._paginator.hasNextPage()) {
      this.dataSource._paginator.nextPage();
      this.dataSource._paginator.previousPage();
      // in case we're on last page this if will tick
    } else if (this.dataSource._paginator.hasPreviousPage()) {
      this.dataSource._paginator.previousPage();
      this.dataSource._paginator.nextPage();
      // in all other cases including active filter we do it like this
    } else {
      this.dataSource.filter = '';
      this.dataSource.filter = this.filter.nativeElement.value;
    }
  }

  public loadData() {
    this.exampleDatabase = new DataService(this.httpClient);
    this.dataSource = new ExampleDataSource(this.exampleDatabase, this.paginator, this.sort);
    Observable.fromEvent(this.filter.nativeElement, 'keyup')
      .debounceTime(150)
      .distinctUntilChanged()
      .subscribe(() => {
        if (!this.dataSource) {
          return;
        }
        this.dataSource.filter = this.filter.nativeElement.value;
      });
  }
}


export class ExampleDataSource extends DataSource<Issue> {
  _filterChange = new BehaviorSubject('');

  get filter(): string {
    return this._filterChange.value;
  }

  set filter(filter: string) {
    this._filterChange.next(filter);
  }

  filteredData: Issue[] = [];
  renderedData: Issue[] = [];

  constructor(public _exampleDatabase: DataService,
              public _paginator: MatPaginator,
              public _sort: MatSort) {
    super();
    // Reset to the first page when the user changes the filter.
    this._filterChange.subscribe(() => this._paginator.pageIndex = 0);
  }

  /** Connect function called by the table to retrieve one stream containing the data to render. */
  connect(): Observable<Issue[]> {
    // Listen for any changes in the base data, sorting, filtering, or pagination
    const displayDataChanges = [
      this._exampleDatabase.dataChange,
      this._sort.sortChange,
      this._filterChange,
      this._paginator.page
    ];

    this._exampleDatabase.getAllIssues();

    return Observable.merge(...displayDataChanges).map(() => {
      // Filter data
      this.filteredData = this._exampleDatabase.data.slice().filter((issue: Issue) => {
        const searchStr = (issue.menu + issue.cost).toLowerCase();
        return searchStr.indexOf(this.filter.toLowerCase()) !== -1;
      });

      // Sort filtered data
      const sortedData = this.sortData(this.filteredData.slice());

      // Grab the page's slice of the filtered sorted data.
      const startIndex = this._paginator.pageIndex * this._paginator.pageSize;
      this.renderedData = sortedData.splice(startIndex, this._paginator.pageSize);
      return this.renderedData;
    });
  }
  disconnect() {
  }



  /** Returns a sorted copy of the database data. */
  sortData(data: Issue[]): Issue[] {
    if (!this._sort.active || this._sort.direction === '') {
      return data;
    }

    return data.sort((a, b) => {
      let propertyA: number | string = '';
      let propertyB: number | string = '';

      switch (this._sort.active) {
        //case 'id': [propertyA, propertyB] = [a.id, b.id]; break;
        case 'title': [propertyA, propertyB] = [a.menu, b.menu]; break;
        case 'state': [propertyA, propertyB] = [a.cost, b.cost]; break;
        //case 'url': [propertyA, propertyB] = [a.url, b.url]; break;
        //case 'created_at': [propertyA, propertyB] = [a.created_at, b.created_at]; break;
        //case 'updated_at': [propertyA, propertyB] = [a.updated_at, b.updated_at]; break;
      }

      const valueA = isNaN(+propertyA) ? propertyA : +propertyA;
      const valueB = isNaN(+propertyB) ? propertyB : +propertyB;

      return (valueA < valueB ? -1 : 1) * (this._sort.direction === 'asc' ? 1 : -1);
    });
  }
}
