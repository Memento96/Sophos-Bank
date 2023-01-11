import {Component, OnInit, Input, Output, EventEmitter} from '@angular/core';
import {ProductsService} from "../../../../core/services/products.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";


@Component({
  selector: 'app-product-form',
  templateUrl: './product-form.component.html',
  styleUrls: ['./product-form.component.scss']
})
export class ProductFormComponent implements OnInit {

  @Input()
  isModify = false;

  @Output() formData: EventEmitter<any> = new EventEmitter();

  form: FormGroup;

  constructor(private productServices: ProductsService,
              private fb: FormBuilder) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      clientId: [''],
      accountType: [''],
      GMF: [true]
    });
  }


  send(){
    this.formData.emit(this.form.value);
  }

  search() {
    console.log('error')
  }



}
