import { first } from 'rxjs/operators';
import { AdminService } from './admin.service';
import { Component, OnInit } from '@angular/core';
import { store } from '@angular/core/src/render3/instructions';
import { ItemAdminDetail } from '../model/item-navbar-admin-detail';
import { SystemService } from '../common/system/system.service';
import { ItemNavbarAdmin } from '../model/item-navbar-admin';


const NOTIFICATION: Number = 1;
const STORES: Number = 2;
const HELP: Number = 3;

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  navbarTopStore: ['ZuanShop'];
  notification: ItemNavbarAdmin[] = new Array();
  stores: ItemNavbarAdmin[] = new Array();
  helpItems: ItemNavbarAdmin[] = new Array();

  constructor(private adminService: AdminService, private systemService: SystemService) { }

  ngOnInit() {
    this.loadNavbarItems();
  }

  loadNavbarItems() {
    this.adminService.getNavbarItems().pipe(first()).subscribe(data => {
      data.forEach(item => {
        switch (item.group) {
          case NOTIFICATION:
            this.notification.push(item);
            break;
          case STORES:
            this.stores.push(item);
            break;
          case HELP:
            this.helpItems.push(item);
            break;
          default:
            break;
        }
      });
    });
  }
}
