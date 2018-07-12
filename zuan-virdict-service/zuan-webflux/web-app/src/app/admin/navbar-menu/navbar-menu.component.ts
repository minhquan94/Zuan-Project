import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-navbar-menu',
  templateUrl: './navbar-menu.component.html',
  styleUrls: ['./navbar-menu.component.css', '../admin.component.css']
})
export class NavbarMenuComponent implements OnInit {

  @Input()
  isShowMenuBar: boolean

  menuDashboard = ['Bảng Điều'];

  constructor() { }

  ngOnInit() {
  }

}
