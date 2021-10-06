import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';
import {Issue} from '../models/issue';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import { environment } from '../../environments/environment';

@Injectable()
export class DataService {
  //private  API_URL = 'http://gajoku-env-1.remcewpict.ap-northeast-2.elasticbeanstalk.com/editmenu?r_admin=thejinkuk&id=15';
  private lo_API_URL = `${environment.apiBaseUrl}`;
  dataChange: BehaviorSubject<Issue[]> = new BehaviorSubject<Issue[]>([]);
  // Temporarily stores data from dialogs
  dialogData: any;

  constructor (private httpClient: HttpClient) {}

  get data(): Issue[] {
    return this.dataChange.value;
  }

  getDialogData() {
    console.log('in getdialogdata is :', this.dialogData);
    localStorage.getItem('dialogdatacost');
    localStorage.getItem('dialogdatamenu');
    this.dialogData = {cost: localStorage.getItem('dialogdatacost'), menu: localStorage.getItem('dialogdatamenu')};
    return this.dialogData;
  }

  /** CRUD METHODS */
  getAllIssues(): void {
    let obj: Issue[];
    console.log('in getallissue : ', localStorage.getItem('token'));
    this.httpClient.get<Issue[]>(`${this.lo_API_URL}/editmenu?r_admin=` + localStorage.getItem('token')).subscribe(data => {
        obj = JSON.parse(data[0]['restaurantMenu']);
        this.dataChange.next(obj);
      },
      (error: HttpErrorResponse) => {
      console.log (error.name + ' ' + error.message);
      });
  }

  //아래꺼 제대로 들어감
  addIssue (issue: Issue): void {
    this.dialogData = issue;
    console.log('in addIssue dialogdata is :', this.dialogData.cost);
    localStorage.setItem('dialogdatacost', this.dialogData.cost);
    localStorage.setItem('dialogdatamenu', this.dialogData.menu);
  }

  updateIssue (issue: Issue): void {
    this.dialogData = issue;
    localStorage.setItem('dialogdatacost', this.dialogData.cost);
    localStorage.setItem('dialogdatamenu', this.dialogData.menu);
  }

  deleteIssue (menu: string): void {
    console.log();
    localStorage.setItem('dialogdatacost', this.dialogData.cost);
    localStorage.setItem('dialogdatamenu', this.dialogData.menu);
  }
}



/* REAL LIFE CRUD Methods I've used in my projects. ToasterService uses Material Toasts for displaying messages:

    // ADD, POST METHOD
    addItem(kanbanItem: KanbanItem): void {
    this.httpClient.post(this.API_URL, kanbanItem).subscribe(data => {
      this.dialogData = kanbanItem;
      this.toasterService.showToaster('Successfully added', 3000);
      },
      (err: HttpErrorResponse) => {
      this.toasterService.showToaster('Error occurred. Details: ' + err.name + ' ' + err.message, 8000);
    });
   }

    // UPDATE, PUT METHOD
     updateItem(kanbanItem: KanbanItem): void {
    this.httpClient.put(this.API_URL + kanbanItem.id, kanbanItem).subscribe(data => {
        this.dialogData = kanbanItem;
        this.toasterService.showToaster('Successfully edited', 3000);
      },
      (err: HttpErrorResponse) => {
        this.toasterService.showToaster('Error occurred. Details: ' + err.name + ' ' + err.message, 8000);
      }
    );
  }

  // DELETE METHOD
  deleteItem(id: number): void {
    this.httpClient.delete(this.API_URL + id).subscribe(data => {
      console.log(data['']);
        this.toasterService.showToaster('Successfully deleted', 3000);
      },
      (err: HttpErrorResponse) => {
        this.toasterService.showToaster('Error occurred. Details: ' + err.name + ' ' + err.message, 8000);
      }
    );
  }
*/




