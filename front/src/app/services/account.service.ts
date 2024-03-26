import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserService } from './user.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  private apiUrl = 'api/account/';

  constructor(private http: HttpClient, private userService: UserService) { }

  createAccount(data:any){
    const headers = this.userService.getHeaders()
    return this.http.post<any>(`${this.apiUrl}add`,data, {headers})
  }
  getAccount(id:any){
    const headers = this.userService.getHeaders()
    return this.http.get<any>(`${this.apiUrl}get/${id}`,{headers})
  }
  depositMoney(number:any, sum:number){
    const headers = this.userService.getHeaders()
    return this.http.post<any>(`${this.apiUrl}deposit`,{number, sum} ,{headers})
  }
  lockAccount(number:any){
    const headers = this.userService.getHeaders()
    return this.http.post<any>(`${this.apiUrl}${number}/block`,{} ,{headers})
  }
  unlockAccount(number:any){
    const headers = this.userService.getHeaders()
    return this.http.post<any>(`${this.apiUrl}${number}/unblock`,{} ,{headers})
  }
  getAccounts(): Observable<any[]> {
    const headers = this.userService.getHeaders()
    return this.http.get<any[]>(`${this.apiUrl}get`, {headers});
  }
}
