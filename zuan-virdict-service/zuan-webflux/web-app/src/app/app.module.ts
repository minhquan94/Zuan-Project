import { AuthGuard } from './guards/auth-guard';
import { JwtInterceptor } from './helpers/jwt.interceptor';
import { ErrorInterceptor } from './helpers/error.interceptor';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AngularFontAwesomeModule } from 'angular-font-awesome';
import { RouterModule, Routes } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NO_ERRORS_SCHEMA } from '@angular/core';

import { AppComponent } from './app.component';
import { AdminComponent } from './admin/admin.component';
import { LoginComponent } from './admin/login/login.component';
import { NavbarComponent } from './shop/navbar/navbar.component';
import { ShopComponent } from './shop/shop.component';
import { AlertComponent } from './common/alert/alert.component';
import { AlertService } from './common/alert/alert.service';
import { EmailComponent } from './admin/email/email.component';
import { NavbarMenuComponent } from './admin/navbar-menu/navbar-menu.component';
import { NavbarTopComponent } from './admin/navbar-top/navbar-top.component';
import { AdminModule } from './admin/admin.module';

const appRoutes: Routes = [
  { path: 'admin/email', component: EmailComponent },
  { path: 'login/admin', component: LoginComponent },
  { path: 'shop', component: ShopComponent },
  { path: 'admin', component: AdminComponent, canActivate: [AuthGuard] }
];

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    AlertComponent,
    ShopComponent
  ],
  imports: [
    AdminModule,
    BrowserModule,
    AngularFontAwesomeModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule.forRoot(
      appRoutes,
      { enableTracing: false } // <-- debugging purposes only
    )
  ],
  providers: [
    AuthGuard,
    AlertService,
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
  ],
  schemas: [NO_ERRORS_SCHEMA],
  bootstrap: [AppComponent]
})
export class AppModule { }
