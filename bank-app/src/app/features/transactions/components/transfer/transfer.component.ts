import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ProductsService } from 'src/app/core/services/products.service';

@Component({
  selector: 'app-transfer',
  templateUrl: './transfer.component.html',
  styleUrls: ['./transfer.component.scss']
})
export class TransferComponent implements OnInit {

  error: any

  form: FormGroup;

  constructor(private productServices: ProductsService,
              private fb: FormBuilder) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      recipient: [''],
      sender: [''],
      description: [''],
      transactionAmount: ['']
    });
  }


  send(){
    console.log('hello deposit')
    const body = {
      recipient: this.form.get('recipient')?.value,
      sender: this.form.get('sender')?.value,
      description: this.form.get('description')?.value,
      transactionType: "TRANSFER",
      transactionAmount: this.form.get('transactionAmount')?.value
    }

    this.productServices.createTransaction(body).subscribe({
      next: () => {
        console.log("The transaction has been succesful")
        alert("The transaction has been succesful")
      },
      error: (e) => {
        this.error = e
        console.log(e?.error?.error);
        console.log(e)
      },
      complete: () => console.log('done'),
    });
  }
  
  resetForm() {
    this.form.reset();
  }

}
