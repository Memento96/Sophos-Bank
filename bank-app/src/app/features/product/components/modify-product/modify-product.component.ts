import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { catchError, Observable, of } from 'rxjs';
import { ProductsService } from 'src/app/core/services/products.service';

@Component({
  selector: 'app-modify-product',
  templateUrl: './modify-product.component.html',
  styleUrls: ['./modify-product.component.scss']
})
export class ModifyProductComponent implements OnInit {

  error: any;

  form: FormGroup;

  product$: Observable<any>;

  errorMessage: string;

  constructor(private productService: ProductsService,
              private fb: FormBuilder) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      productId: ['']
    })
  }

  getProductById(): void{
    const id = this.form.get('productId')?.value
    this.product$ = this.productService.getProductById(id).pipe(
      catchError(error => {
        this.errorMessage = error?.error.message;
        return of(null);
      })
      );
  }

  modify(data: any){
    this.productService.modifyProduct(data).subscribe({
      next: () => {
        console.log("Product has been modified")
        alert("Product has been modified")
      },
      error: (e) => {
        this.error = e
        console.log(e?.error?.error);
        console.log(e)
      },
      complete: () => {console.log('done');
      this
    },
    });
  }

}
