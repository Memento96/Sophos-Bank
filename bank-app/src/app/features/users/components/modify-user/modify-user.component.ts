import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { catchError, Observable, of } from 'rxjs';
import { ProductsService } from 'src/app/core/services/products.service';

@Component({
  selector: 'app-modify-user',
  templateUrl: './modify-user.component.html',
  styleUrls: ['./modify-user.component.scss']
})
export class ModifyUserComponent implements OnInit {

  error: any

  form: FormGroup;
  
  user$: Observable<any>

  errorMessage: string;

  constructor(private productService: ProductsService,
              private fb: FormBuilder) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      clientId: ['']
    })
  }

  getClientById(): void {
    const id = this.form.get('clientId')?.value;
    this.user$ = this.productService.getClientById(id).pipe(
      catchError(error => {
        this.errorMessage = error?.error.message;
        return of(null);
      })
      );
  }


  modify(data:any){
    const id = this.form.get('clientId')?.value
    this.productService.modifyClient(data, id).subscribe({
      next: () => {
        console.log("Client has been successfuly modified")
        alert("Client has been successfuly modified")
      },
      error: (e) => {
        this.error = e
        console.log(e?.error?.error);
        console.log(e)
      },
      complete: () => console.log('done'),
    });
  }

  delete(){
    const id = this.form.get('clientId')?.value
    this.productService.deleteClient(id).subscribe({
      next: () => {
        console.log("Cliente deleted")
      },
      error: (e) => {
        console.log(e.error.error);
      },
      complete: () => console.log('done'),
    });
  }

  resetForm() {
    this.form.reset();
  }

}
