import { RouterModule, Routes } from '@angular/router';
import { ErrorInterceptor } from './../helpers/error.interceptor';
import { JwtInterceptor } from './../helpers/jwt.interceptor';
import { AlertService } from './../alert/alert.service';
import { AuthGuard } from './../guards/auth-guard';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { AngularFontAwesomeModule } from 'angular-font-awesome';
import { BrowserModule } from '@angular/platform-browser';
import { NavbarMenuComponent } from './navbar-menu/navbar-menu.component';
import { NavbarTopComponent } from './navbar-top/navbar-top.component';
import { AdminComponent } from './admin.component';
import { NgModule, NO_ERRORS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login/login.component';
import { EmailComponent } from './email/email.component';

const appRoutes: Routes = [
  { path: 'admin/email', component: EmailComponent },
  { path: 'login/admin', component: LoginComponent },
  { path: 'admin', component: AdminComponent, canActivate: [AuthGuard] }
];

@NgModule({
  imports: [
    BrowserModule,
    AngularFontAwesomeModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule.forRoot(
      appRoutes,
      { enableTracing: false } // <-- debugging purposes only
    )
  ],
  declarations: [
    AdminComponent, 
    NavbarTopComponent, 
    NavbarMenuComponent,
    LoginComponent,
    EmailComponent
   ],
   providers: [
    AuthGuard,
    AlertService,
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
  ],
  schemas: [NO_ERRORS_SCHEMA],
  bootstrap: [AdminComponent]
})
export class AdminModule { }
