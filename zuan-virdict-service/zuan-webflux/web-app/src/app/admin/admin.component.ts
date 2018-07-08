import { ItemNavbarAdmin } from './../model/item-navbar-admin';
import { first } from 'rxjs/operators';
import { AdminService } from './admin.service';
import { Component, OnInit } from '@angular/core';
import { store } from '@angular/core/src/render3/instructions';
import { ItemAdminDetail } from '../model/item-navbar-admin-detail';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  navbarTopStore: ["ZuanShop"];
  stores: ItemNavbarAdmin[] = new Array();
  helpItems: ItemNavbarAdmin[] = new Array();

  constructor(private adminService: AdminService) { }

  ngOnInit() {
    this.loadStores();
    this.loadHelpItems();
  }

  loadStores() {
    let storesLoaded = this.adminService.getStores();
    let itemDetail: ItemAdminDetail[] = new Array();
    storesLoaded.forEach(element => {
      itemDetail.push(new ItemAdminDetail(element.storeName, element.storeLink));
    });
    this.stores.push(new ItemNavbarAdmin("Cửa hàng", itemDetail));
  }

  loadHelpItems() {
    let helpItemsLoaded = this.adminService.getHelpItems();
    let itemDetail: ItemAdminDetail[] = new Array();
    helpItemsLoaded.forEach(element => {
      itemDetail.push(new ItemAdminDetail(element.helpName, element.helpLink));
    });
    this.helpItems.push(new ItemNavbarAdmin("Trợ giúp", itemDetail));
  }
}
