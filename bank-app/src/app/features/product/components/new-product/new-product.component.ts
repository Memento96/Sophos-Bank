import { Component, OnInit } from '@angular/core';
import { ProductsService } from 'src/app/core/services/products.service';

@Component({
  selector: 'app-new-product',
  templateUrl: './new-product.component.html',
  styleUrls: ['./new-product.component.scss']
})
export class NewProductComponent implements OnInit {

  error: any;

  constructor(private productService: ProductsService) { }

  ngOnInit(): void {
  }

  create(data:any){

    this.productService.createProduct(data).subscribe({
          next: () => {
            console.log("Product has been created")
            alert("Product has been created")
          },
          error: (e) => {
            this.error = e
            console.log(e?.error?.error);
            console.log(e);
            // alert(e?.error?.message);
          },
          complete: () => {
            console.log('done');
            this.error = {}
          } 
        });

}

}
