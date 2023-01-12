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
    const body = {
      idType: params.idType,
      idNumber: params.idNumber,
      names: params.name,
      lastNames: params.lastName,
      emailAddress: params.email,
      dateOfBirth: params.dataOfBirth
  }
    return this.http.post(`${environment.apiUrl}/client`, body)

  }

  getAllClients():Observable<any>{

    return this.http.get(`${environment.apiUrl}/client`)

  }

  getClientById(clientId: string): Observable<any>{

    return this.http.get(`${environment.apiUrl}/client/${clientId}`)

  }

  getProductsByClient (clientId: string): Observable<any>{
    
    return this.http.get(`${environment.apiUrl}/client/${clientId}/product`)

  }

  modifyClient(params: any, id: number): Observable<any>{
    const body = {
        id: id,
        names: params.name,
        lastNames: params.lastName,
        emailAddress: params.email
  }
    return this.http.put<any>(`${environment.apiUrl}/client`, body)
  }


  deleteClient (clientId: number): Observable<any>{
    
    return this.http.delete(`${environment.apiUrl}/client/${clientId}/delete`)

  }


  createProduct (params: any):Observable<any>{

    return this.http.post(`${environment.apiUrl}/product`, params)

  }

  getProductById(productId: string): Observable<any>{

    return this.http.get(`${environment.apiUrl}/product/${productId}`)

  }

  getAllProducts(): Observable<any>{

    return this.http.get(`${environment.apiUrl}/all`)

  }

  getTransactionByProduct (id: string): Observable<any> {

    return this.http.get(`${environment.apiUrl}/product/${id}/transactions`)

  }

  modifyProduct(params: any): Observable<any>{
    const body = {
      id: params.productId,
      gmfExempt: params.GMF,
      accountStatus: params.accountStatus
  }
    return this.http.put<any>(`${environment.apiUrl}/product`, body)
  }

  createTransaction(params: any){

    return this.http.post(`${environment.apiUrl}/transaction`, params)

  }

}
