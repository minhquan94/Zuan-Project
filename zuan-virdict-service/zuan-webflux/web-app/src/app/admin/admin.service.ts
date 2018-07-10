import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { ItemNavbarAdmin } from '../model/item-navbar-admin';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private httpClient: HttpClient) { }

  getNavbarItems(): Observable<ItemNavbarAdmin[]> {
    return this.httpClient.get<ItemNavbarAdmin[]>('http://localhost:8080/rest/admin/navbar-items').pipe(map(items => {
      return items;
    }));
  }
}
