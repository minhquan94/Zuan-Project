import { first } from 'rxjs/operators';
import { AdminService } from './admin.service';
import { Component, OnInit } from '@angular/core';
import { store } from '@angular/core/src/render3/instructions';
import { ItemAdminDetail } from '../model/item-navbar-admin-detail';
import { SystemService } from '../common/system/system.service';
import { ItemNavbarAdmin } from '../model/item-navbar-admin';
import { ActivatedRoute, Router } from '@angular/router';
import { LoginService } from './login/login.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  constructor(
    private adminService: AdminService, 
    private systemService: SystemService,
    private loginService: LoginService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit() {
    this.validateUser();
  }


  validateUser() {
    const currentUser = localStorage.getItem('currentUser');
    const username = JSON.parse(currentUser).username;
    if (username.startsWith('guest_')) {
      this.router.navigate(['/login/admin'], { queryParams: { returnUrl: '/admin' } });
    }
  }

  logout() {
    localStorage.removeItem('currentUser');
    this.loginService.logout().subscribe(response => {
      this.router.navigate(['/login/admin'], { queryParams: { returnUrl: '/admin' } });
    });
  }
}
