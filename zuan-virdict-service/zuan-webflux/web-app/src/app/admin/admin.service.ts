import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private httpClient: HttpClient) { }

  test() {
    return this.httpClient.get<any>('http://localhost:8080/rest/login/test').pipe(map(response => {
      return response;
    }));
  }

  getStores() {
    return [{storeName: "ZuanShop", storeLink: "shop"}];
  }

  getHelpItems() {
    return [
      {helpName: "Trang chủ ZuanShop", helpLink: "shop"},
      {helpName: "Tài liệu hướng dẫn", helpLink: "shop"},
      {helpName: "Trung tâm hỗ trợ", helpLink: "shop"}
    ] 
  }
}
