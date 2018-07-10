import { first } from 'rxjs/operators';
import { AdminService } from './admin.service';
import { Component, OnInit } from '@angular/core';
import { store } from '@angular/core/src/render3/instructions';
import { ItemAdminDetail } from '../model/item-navbar-admin-detail';
import { SystemService } from '../common/system/system.service';
import { ItemNavbarAdmin } from '../model/item-navbar-admin';
import { ActivatedRoute, Router } from '@angular/router';
import { LoginService } from './login/login.service';


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
  isShowMenu = false;
  currentUser = '';

  constructor(
    private adminService: AdminService, 
    private systemService: SystemService,
    private loginService: LoginService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit() {
    this.validateUser();
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

  validateUser() {
    const currentUser = localStorage.getItem('currentUser');
    const username = JSON.parse(currentUser).username;
    if (username.startsWith('guest_')) {
      this.router.navigate(['/login/admin'], { queryParams: { returnUrl: '/admin' } });
    }
    this.currentUser = JSON.parse(currentUser).username;
  }

  logout() {
    localStorage.removeItem('currentUser');
    this.loginService.logout().subscribe(response => {
      this.router.navigate(['/login/admin'], { queryParams: { returnUrl: '/admin' } });
    });
  }

  isShowMenuBar() {
    this.isShowMenu = !this.isShowMenu;
  }
}
