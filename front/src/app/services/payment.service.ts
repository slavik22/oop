import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {
  private apiUrl = 'api/payment/';

  constructor(private http: HttpClient, private userService: UserService) { }

  transferMoney(from:any, sum:number, to:any){
    const headers = this.userService.getHeaders()
    return this.http.post<any>(`${this.apiUrl}`,{from, to, sum}, {headers})
  }
}
