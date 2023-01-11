import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { BehaviorSubject, Observable } from 'rxjs';
import {ProductsService} from "../../../../core/services/products.service";

@Component({
  selector: 'app-list-product-client',
  templateUrl: './list-product-client.component.html',
  styleUrls: ['./list-product-client.component.scss']
})
export class ListProductClientComponent implements OnInit {

  list$: Observable<any>;
  
  form: FormGroup;

  constructor(private productService: ProductsService, 
              private fb: FormBuilder) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      productId: ['']
    })
  }

  getProductId(): void{
    const id = this.form.value
    this.list$ = this.productService.getProductsByClient(id.productId);
  }

}
