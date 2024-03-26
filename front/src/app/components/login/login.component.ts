import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginForm!:FormGroup;
  constructor(private fb: FormBuilder, private userService: UserService, private router: Router) { }

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      email: ["",Validators.required],
      password: ["",Validators.required]

    });
  }

  onSubmit(){
    if(this.loginForm.valid){
      this.userService.login(this.loginForm.value).subscribe({
        next: res => {
          this.loginForm.reset();
          this.userService.storeToken(res.jwtToken);
          this.router.navigate(['']);
        },
        error: error => {
          alert(error.message);
        }
      })
    }
    else{
      this.loginForm.markAsDirty();
    }
  }
}
