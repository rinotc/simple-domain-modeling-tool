import { Router } from '@angular/router';

export const redirectTo404 = (router: Router) => {
  router.navigateByUrl('/404').then((_) => {});
};
