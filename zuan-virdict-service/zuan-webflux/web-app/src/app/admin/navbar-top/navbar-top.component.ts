import { first } from 'rxjs/operators';
import { ItemNavbarAdmin } from './../../model/item-navbar-admin';
import { AdminService } from './../admin.service';
import { Component, OnInit, Output, EventEmitter } from '@angular/core';

const NOTIFICATION: Number = 1;
const STORES: Number = 2;
const HELP: Number = 3;

@Component({
  selector: 'app-navbar-top',
  templateUrl: './navbar-top.component.html',
  styleUrls: ['./navbar-top.component.css', '../admin.component.css'],
  providers: [AdminService]
})
export class NavbarTopComponent implements OnInit {

  notification: ItemNavbarAdmin[] = new Array();
  stores: ItemNavbarAdmin[] = new Array();
  helpItems: ItemNavbarAdmin[] = new Array();
  isShowMenu = false;
  currentUser = '';
  menuCatalog = false;

  @Output()
  showMenuBar = new EventEmitter<boolean>();

  constructor(
    private adminService: AdminService
  ) { }

  ngOnInit() {
    this.loadNavbarItems();
  }

  loadNavbarItems() {
    this.adminService.getNavbarItems().subscribe(data => {
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

  isShowMenuBar() {
    this.isShowMenu =! this.isShowMenu;
    this.showMenuBar.emit(this.isShowMenu);
  }
}
