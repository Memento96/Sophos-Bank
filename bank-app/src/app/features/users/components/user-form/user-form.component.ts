import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.scss']
})
export class UserFormComponent implements OnInit {

  @Input()
  client: any;

  @Output() formData: EventEmitter<any> = new EventEmitter();

  form: FormGroup;

  constructor(private fb: FormBuilder) { }

  ngOnInit(): void {
    console.log(this.client)
    this.form = this.fb.group({
      idNumber: [this.client ? this.client.idNumber : ''],
      idType: [this.client ? this.client.idType : ''],
      name: [this.client ? this.client.names : ''],
      lastName: [this.client ? this.client.lastNames : ''],
      email: [this.client ? this.client.emailAddress : ''],
      dataOfBirth: [this.client ? this.client.dateOfBirth : ''],
    });
  }

  send(){
    this.formData.emit(this.form.value);
    console.log(this.form.value)
  }

}
