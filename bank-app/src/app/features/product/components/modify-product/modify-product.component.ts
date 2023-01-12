import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Observable } from 'rxjs';
import { ProductsService } from 'src/app/core/services/products.service';

@Component({
  selector: 'app-modify-product',
  templateUrl: './modify-product.component.html',
  styleUrls: ['./modify-product.component.scss']
})
export class ModifyProductComponent implements OnInit {

  form: FormGroup;

  product$: Observable<any>;

  constructor(private productService: ProductsService,
              private fb: FormBuilder) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      productId: ['']
    })
  }

  getProductById(): void{
    const id = this.form.get('productId')?.value
    this.product$ = this.productService.getProductById(id)
  }

  modify(data: any){
    this.productService.modifyProduct(data).subscribe({
      next: () => {
        console.log("I worked")
      },
      error: (e) => {
        console.log(e.error.error);
      },
      complete: () => console.log('done'),
    });
  }

}
