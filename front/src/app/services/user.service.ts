import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';


@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'api/user/';


  constructor(private http: HttpClient, private router: Router) { }

  getHeaders(): any{
    return new HttpHeaders().set('Authorization', 'Bearer ' + this.getToken());
  }

  getUserAccounts(): Observable<any[]> {
    const headers = this.getHeaders();
    return this.http.get<any[]>(`${this.apiUrl}accounts`, {headers});
  }
  register(data:any){
    return this.http.post<any>(`${this.apiUrl}auth/register`,data)
  }
  login(data:any){
    return this.http.post<any>(`${this.apiUrl}auth/login`,data)
  }
  signOut(){
    localStorage.clear();
    this.router.navigate(["login"]);
  }
  storeToken(tokenValue: string){
    localStorage.setItem("token", tokenValue);
  }
  getToken():string{
    const token = localStorage.getItem("token")
    if(token === null) return ""
    return token;
  }
  isLoggedIn():boolean{
    return !!localStorage.getItem("token");
  }

  isAdmin(): boolean {
    const helper = new JwtHelperService();
    const decodedToken = helper.decodeToken(this.getToken());

    return decodedToken.role === "ROLE_ADMIN";
  }
}
