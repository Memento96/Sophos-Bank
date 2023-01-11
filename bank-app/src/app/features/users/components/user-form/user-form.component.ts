import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.scss']
})
export class UserFormComponent implements OnInit {

  @Output() formData: EventEmitter<any> = new EventEmitter();

  form: FormGroup;

  constructor(private fb: FormBuilder) { }

  ngOnInit(): void {

    this.form = this.fb.group({
      idNumber: ['', ],
      idType: ['',],
      name: ['',],
      lastName: ['',],
      email: ['',],
      dataOfBirth: ['',],
    });
  }

  send(){

    this.formData.emit(this.form.value);
  }

}
