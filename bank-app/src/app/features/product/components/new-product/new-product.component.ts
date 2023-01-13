import { Component, OnInit } from '@angular/core';
import { ProductsService } from 'src/app/core/services/products.service';

@Component({
  selector: 'app-new-product',
  templateUrl: './new-product.component.html',
  styleUrls: ['./new-product.component.scss']
})
export class NewProductComponent implements OnInit {

  constructor(private productService: ProductsService) { }

  ngOnInit(): void {
  }

  create(data:any){

    this.productService.createProduct(data).subscribe({
          next: () => {
            console.log("Product has been created")
          },
          error: (e) => {
            console.log(e.error.error);
          },
          complete: () => console.log('done'),
        });

}

}
