import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-email',
  templateUrl: './email.component.html',
  styleUrls: ['./email.component.css']
})
export class EmailComponent implements OnInit {

  searchMailForm: FormGroup;
  sendMailForm: FormGroup;

  constructor(private fb: FormBuilder) { }

  ngOnInit() {
    this.buildSearchForm();
    this.buildSendMailForm();
  }

  buildSearchForm() {
    this.searchMailForm = this.fb.group({
      searchTxt: ['', [Validators.nullValidator]]
    })
  }

  buildSendMailForm() {
    this.searchMailForm = this.fb.group({
      formControlName: ['', [Validators.required]]
    })
  }
}
