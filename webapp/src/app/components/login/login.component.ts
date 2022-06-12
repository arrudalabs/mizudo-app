import { Router } from '@angular/router';
import { AlertController, LoadingController } from '@ionic/angular';
import { SessionService } from './../../services/session.service';
import { Component, OnInit } from '@angular/core';
import {
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { Token } from 'src/app/model/Token';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  username = new FormControl('', [Validators.required]);
  password = new FormControl('', [Validators.required]);

  loginForm: FormGroup = new FormGroup({
    username: this.username,
    password: this.password,
  });

  constructor(
    private sessionService: SessionService,
    private loadingController: LoadingController,
    private alertController: AlertController,
    private router: Router
  ) {}

  ngOnInit(): void {}

  async onSubmit() {

    if (!this.loginForm.valid) {
      return;
    }

    const loading = await this.loadingController
      .create({
        message: 'Please wait...',
      });

    loading.present();

    const stopLoading = ()=>{
      loading.dismiss();
    }

    const username = this.username.value;
    const password = this.password.value;

    this.sessionService
      .auth({
        username,
        password,
      })
      .subscribe({
        next: (token) => this.onSuccess(stopLoading,token),
        error: async(error) => this.onError(stopLoading,error),
      });
  }

  async onError(stopLoading: () => any, error: any): Promise<void> {
    const alertEL = await this.alertController.create({
      message: `Failure on authenticate!`,
      buttons: [
        {
          text: 'Try again',
        },
      ],
    });
    stopLoading();
    alertEL.present();
  }

  onSuccess(stopLoading: () => any, token: Token): void {
    this.router.navigate(['']).finally(() => {
      this.loginForm.reset();
      stopLoading();
    });
  }
}
