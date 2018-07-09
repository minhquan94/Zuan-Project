import { LoginService } from './login.service';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../../model/users';
import { AlertService } from '../../common/alert/alert.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  returnUrl: string;

  constructor(private fb: FormBuilder,
    private loginService: LoginService,
    private alertService: AlertService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit() {
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
    this.buildLoginForm();
  }

  buildLoginForm() {
    this.loginForm = this.fb.group({
      username: ['', [Validators.required]],
      password: ['', Validators.required],
    });
  }

  login(form: FormGroup) {
    const username = form.value.username;
    const password = form.value.password;
    const user: User = new User(username, password);
    this.loginService.login(user).pipe(first()).subscribe(data => {
      this.router.navigate([this.returnUrl]);
    }, error => {
      this.alertService.error('Thông tin đăng nhập không chính xác.');
    });
  }
}
