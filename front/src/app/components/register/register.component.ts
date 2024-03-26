import { Component } from '@angular/core';
import { FormBuilder,FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  signupForm!:FormGroup;
  constructor(private fb: FormBuilder, private userService: UserService, private router: Router) { }

  ngOnInit(): void {
    this.signupForm = this.fb.group({
      name: ["",Validators.required],
      email: ["",Validators.required],
      password: ["",Validators.required],
    });
  }
  onSubmit(){
    if(this.signupForm.valid){
      this.userService.register(this.signupForm.value).subscribe({
        next: res => {
          this.signupForm.reset();
          this.userService.storeToken(res.jwtToken);
          this.router.navigate(['']);
        },
        error: error => {
          alert(error.error.message)

        }
      })
    }
  }
}
