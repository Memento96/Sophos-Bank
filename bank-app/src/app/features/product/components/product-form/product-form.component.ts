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
  
  @Input()
  product: any

  @Output() formData: EventEmitter<any> = new EventEmitter();

  form: FormGroup;

  constructor(private productServices: ProductsService,
              private fb: FormBuilder) { }

  ngOnInit(): void {
    console.log
    this.form = this.fb.group({
      productId: [this.product ? this.product.id : ''],
      accountType: [this.product ? this.product.accountType : ''],
      accountStatus: [this.product ? this.product.accountStatus : ''],
      GMF: [this.product ? this.product.gmfExempt : '']
    });
  }


  send(){
    this.formData.emit(this.form.value);
  }

  search() {
    console.log('error')
  }



}
