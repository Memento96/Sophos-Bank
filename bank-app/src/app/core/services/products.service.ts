import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';


@Injectable({
  providedIn: 'root'
})
export class ProductsService {

  constructor(private http: HttpClient) { }

  createClient (params: any):Observable<any>{

    return this.http.post(`${environment.apiUrl}/client`, params)

  }

  getAllClients(){

    return this.http.get(`${environment.apiUrl}/client/`)

  }

  getClientById(clientId: string): Observable<any>{

    return this.http.get(`${environment.apiUrl}/client/${clientId}`)

  }

  getProductsByClient (clientId: string): Observable<any>{
    
    return this.http.get(`${environment.apiUrl}/client/${clientId}/product`)

  }

  modifyClient (){
    const body = { title: 'Angular PUT Request Example' };
    this.http.put<any>(`${environment.apiUrl}/client`, body)
  }


  deleteClient (clientId: string): Observable<any>{
    
    return this.http.delete(`${environment.apiUrl}/client/${clientId}`)

  }


  createProduct (params: any):Observable<any>{

    return this.http.post(`${environment.apiUrl}/product`, params)

  }

  getProductById(productId: string): Observable<any>{

    return this.http.get(`${environment.apiUrl}/${productId}`)

  }

  getAllProducts(): Observable<any>{

    return this.http.get(`${environment.apiUrl}/all`)

  }

  getTransactionByProduct (id: string): Observable<any> {

    return this.http.get(`${environment.apiUrl}/product/${id}/transactions`)

  }

  // modifyProduct(){
  //   const body = { 
  //     clientId: {''},
  //     accountType: {''},
  //     GMF: {true}
  //   };
  //   this.http.put<any>(`${environment.apiUrl}/product`, body)
  // }

  createTransaction(params: any){

    return this.http.post(`${environment.apiUrl}/transaction`, params)

  }

}
