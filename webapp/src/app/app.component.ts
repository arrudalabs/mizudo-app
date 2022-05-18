import { Component } from '@angular/core';
import { AlertController } from '@ionic/angular';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  title = 'mizudo-app';

  constructor(private alertController: AlertController) {}

  async openAlert() {
    const alert = this.alertController.create({
      header: 'Opa!!!',
      message: 'Usando IONIC com Angular',
      backdropDismiss: false,
      buttons: [{
        text:'Que legal',
        role:'close',
        handler:()=>{
          console.log('vc gostou né?!');
        }
      }],
    });
    (await alert).present();
  }
}
