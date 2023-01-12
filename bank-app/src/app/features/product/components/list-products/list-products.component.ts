import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Observable } from 'rxjs';
import { ProductsService } from '../../../../core/services/products.service';


@Component({
  selector: 'app-list-products',
  templateUrl: './list-products.component.html',
  styleUrls: ['./list-products.component.scss']
})
export class ListTransactionsByProductsComponent implements OnInit {

//This is the list of transactions by product

  list$: Observable<any>;
  
  formTransactionsByProduct: FormGroup;


  constructor(private productService: ProductsService,
              private fb: FormBuilder) { }

  ngOnInit(): void {

    this.formTransactionsByProduct = this.fb.group({
      productTransactionId: ['']
    })

  }

  getTransactionsByProduct(): void{
    const id = this.formTransactionsByProduct.value
    this.list$ = this.productService.getTransactionByProduct(id.productTransactionId);
  }


}
