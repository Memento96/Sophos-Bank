import { Component, OnInit } from '@angular/core';
import { ProductsService } from '../../../../core/services/products.service';


@Component({
  selector: 'app-list-products',
  templateUrl: './list-products.component.html',
  styleUrls: ['./list-products.component.scss']
})
export class ListTransactionsByProductsComponent implements OnInit {

  list$ = this.productService.getProductsByClient('tt')


  constructor(private productService: ProductsService) { }

  ngOnInit(): void {

    // this.productService.getProductsByClient('tst').subscribe(list=> {});

  }



}
