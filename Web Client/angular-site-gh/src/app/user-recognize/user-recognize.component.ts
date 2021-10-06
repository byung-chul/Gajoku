import { Component, OnInit, Pipe, PipeTransform, ViewChild} from '@angular/core';
import { MatTableDataSource} from '@angular/material';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import { environment } from '../../environments/environment';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';
import {SelectionModel} from '@angular/cdk/collections';
import {Observable} from 'rxjs/Rx';



export interface Element {
  customerAddress: string;
  customerDetail: string;
  customerId: string;
  orderContent: string;
  orderCost: number;
  orderId: number;
  orderSurplus: number;
  orderType: number;
  restaurantId: string;
  timestamp: string;
  matchingId: number;
  retime: string;
}
interface Element2 {
  cost: string;
  menu: string;
  quantity: string;
}

@Component({
  selector: 'app-user-recognize',
  templateUrl: './user-recognize.component.html',
  styleUrls: ['./user-recognize.component.css'],
})

export class UserRecognizeComponent implements OnInit {
  displayedColumns = ['customerId', 'customerAddress', 'orderCost', 'timestamp', 'actions', 'actions3'];
  displayedColumns2 = ['cost', 'menu', 'quantity'];

//  dataSource = ELEMENT_DATA;
//  dataSource = new MatTableDataSource<Element>(ELEMENT_DATA);

  dataSource;
  dataSource2;
  element: Element[];
  private apiBaseUrl = `${environment.apiBaseUrl}`;
  menu_index: number;
  dataChange: BehaviorSubject<Element2[]> = new BehaviorSubject<Element2[]>([]);
  selection = new SelectionModel<string>(true, []);
  private lo_API_URL = `${environment.apiBaseUrl}`;

  pollingData: any;

  public checkindex: number;
  public savelist: number[] = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19];
  sendorder: Array<number> = new Array<number>();
  public ordercount = 0;
  public orderupdown = 0; // 주문 받고 보낼때에 orderId를 바꾸어 저장하고 보낼 때

  /*highlight(element: Element){
    element.highlighted = !element.highlighted;
  }*/
  constructor(
    public httpClient: HttpClient,
  ) {

    // Polling is here
    this.pollingData = Observable.interval(4000)
      //.switchMap(() => httpClient.get<Element[]>(`${this.apiBaseUrl}/order?r_id=` + localStorage.getItem('restaurantId')))
      .switchMap(() => httpClient.get<Element[]>(`${this.apiBaseUrl}/order?r_id=` + localStorage.getItem('restaurantId')))
      .subscribe((data) => {
        console.log('6초 경과 recall orderlist');
        this.menulist();
      });
  }

  ngOnInit() {
    this.menulist();
  }

  //to call orderlist, not used in html
  menulist(): void {
    console.log('in menulist button');
    let obj: Element[];
    let obj2: Element2[];

    console.log('login restaurant id is : ', localStorage.getItem('restaurantId'));
    this.httpClient.get<Element[]>(`${this.apiBaseUrl}/order?r_id=` + localStorage.getItem('restaurantId')).subscribe(
      data => {
        console.log(data);
        if (data.length !== 0) {
          if (data[0].orderId === 0) {
            this.orderupdown = 0;
          } else {
            this.orderupdown = data[0].orderId - 1;
          }

          for (let i = 0; i < data.length; i++) {
            // timestamp 짤라서 다시 넣기
            data[i].timestamp = data[i].timestamp.substring(11, 19);

            data[i].orderId = data[i].orderId - this.orderupdown;

            if (data[i].matchingId !== 0) {
              data[i].matchingId = data[i].matchingId - this.orderupdown;
            }

            if (data[i].matchingId !== 0) {
              for (let j = 0; j < data.length; j++) {
                if (data[j].orderId === data[i].matchingId) {
                  data[j].matchingId = data[i].matchingId;
                  this.savelist[j] = data[i].matchingId;
                }
              }
            }
          }

          console.log('tiem is : ', data[0].timestamp);
        }
//        console.log(data[0]['orderContent']);
//        obj = JSON.parse(data[0]['orderContent']);

        const rows = [];
        data.forEach(element => rows.push(element, { detailRow: true, element }));

        this.dataSource = new MatTableDataSource<Element>(data);

        //만약 받은게 없으면 두번째 테이블 리셋
        if (data.length === 0) {
          this.dataSource2 = null;
        }
      }
    );
  }

  returnindex(): number {
    return this.checkindex;
  }

  // html에서 mat-table내에 mat-row에 쓰이는 matchingId를 비교하여 색을 칠해주는데 일단 보류
  findmatch(): number {
    for (let i = 0; i < 20 ; i++) {
      if (this.savelist[i] !== 0) {
        console.log('in findmatch() i : ', this.savelist[i]);
        return this.savelist[i];
      }
    }
  }

  menulist2(index: number): void {
    console.log('menulist2 index : ', index);
    console.log('in menulist button');

    this.checkindex = index;
    console.log('checkindex : ', this.checkindex);
    let obj: Element2[];
    this.httpClient.get<Element[]>(`${this.apiBaseUrl}/order?r_id=` + localStorage.getItem('restaurantId')).subscribe(
      data => {
        console.log(data);
        console.log(data.length);
        /*
        const list1: number[] = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19];
        for (let i = 0; i < data.length; i++) {
          list1[i] = data[i].orderId;
        }
        for (let j = 0; j < data.length; j++) {
          if (list1[j] === index) {
            currentindex = j;
          }
        }
        */
//        console.log(data[0]['orderContent']);
        //        console.log(JSON.stringify(data[1].orderContent));
        obj = JSON.parse(data[index - 1]['orderContent']);
        console.log(obj);
        this.dataSource2 = new MatTableDataSource<Element2>(obj);
      }
    );
  }

//해당하는 행의 index 리턴 접수버튼
  clickpush(index: number) {
    console.log('clickpush1');
    console.log(index);
    this.menu_index = index;

    this.sendorder.push(index);
    console.log('in clickpush sendorder : ', this.sendorder[0]);
    console.log('in clickpush sendorder length : ', this.sendorder.length);
    this.ordercount++;

    const sendbody = {
      o_id: index + this.orderupdown,
    };

    this.httpClient.post(`${this.lo_API_URL}/order/change`, sendbody, {responseType : 'text'}).subscribe(data => {
      console.log(data);
    });

  }

  clickpush2() {
    console.log('clickpush2');

  }
  //보내기
  clickpush3() {
    console.log('clickpush3');

    this.sendorder.sort();
    const sendbody = {
      o_id: this.sendorder[0] + this.orderupdown,
    };
    this.httpClient.post(`${this.lo_API_URL}/order/end`, sendbody, {responseType : 'text'}).subscribe(data => {
      console.log(data);
    });
    for (let i = 0; i < this.ordercount; i++) {
      this.sendorder.pop();
    }
    console.log('in clickpush3() send sendorder.length : ', this.sendorder.length);
    this.ordercount = 0;
  }
}

/*
//연습용 post
  menulist2(): void {
    console.log('in menulist button2');
    let admin = 'thejinkuk';
    let aaa = [{cost: '3000', menu: '알밥'},{cost: '4000', menu: '큰밥'}];
//    let aaa = {cost: '3000', menu: '알밥'};
    let sendbody = {
      r_admin: admin,
      r_menu: JSON.stringify(aaa),
    }
//    JSON.stringify(aaa);

    this.http.post(`${this.apiBaseUrl}/editmenu`, sendbody, {responseType: 'text'}).subscribe(
      data => {
        console.log(data);
        alert('ok');
      },
      error => {
        console.log(JSON.stringify(error.json()));
      }
    );
  }

  menulist3(): void {
    let obj : Issue[];
    this.http.get<Issue[]>(`${this.apiBaseUrl}/editmenu?r_admin=thejinkuk&id=15`).subscribe(data => {
        console.log(data);
        obj = JSON.parse(data[0]['restaurantMenu']);
        console.log(obj);
      });
  }

 */



/*
export interface Element {
  name: string;
  position: number;
  weight: number;
  symbol: string;
}

const ELEMENT_DATA: Element[] = [
  {position: 1, name: 'Hydrogen', weight: 1.0079, symbol: 'H'},
  {position: 2, name: 'Helium', weight: 4.0026, symbol: 'He'},
  {position: 3, name: 'Lithium', weight: 6.941, symbol: 'Li'},
  {position: 4, name: 'Beryllium', weight: 9.0122, symbol: 'Be'},
  {position: 5, name: 'Boron', weight: 10.811, symbol: 'B'},
  {position: 6, name: 'Carbon', weight: 12.0107, symbol: 'C'},
  {position: 7, name: 'Nitrogen', weight: 14.0067, symbol: 'N'},
  {position: 8, name: 'Oxygen', weight: 15.9994, symbol: 'O'},
  {position: 9, name: 'Fluorine', weight: 18.9984, symbol: 'F'},
  {position: 10, name: 'Neon', weight: 20.1797, symbol: 'Ne'},
];
*/

/*
        for (let i = 0; i < data.length; i++) {
          if (data[i].matchingId !== 0) {
            console.log('in menulist call matchingid : ', data[i].matchingId);
            console.log('in menulist call find index : ', i);
            console.log('in menulist call data.length : ', data.length);
            // matchingId가 0이 아닌 다른수가 들어오면 해당하는 index의 matchingId를 같게 savelist에 저장
            this.savelist[data[i].matchingId + 1] = data[i].matchingId;
            this.savelist[i + 1] = data[i].matchingId;
            data[data.length - i - 1].matchingId = data[i].matchingId;
          }
        }
*/
