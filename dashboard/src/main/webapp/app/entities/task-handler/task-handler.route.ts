import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { TaskHandlerComponent } from './task-handler.component';
import { TaskHandlerDetailComponent } from './task-handler-detail.component';
import { TaskHandlerPopupComponent } from './task-handler-dialog.component';
import { TaskHandlerDeletePopupComponent } from './task-handler-delete-dialog.component';

import { Principal } from '../../shared';


export const taskHandlerRoute: Routes = [
  {
    path: 'task-handler',
    component: TaskHandlerComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'dashboardApp.taskHandler.home.title'
    }
  }, {
    path: 'task-handler/:id',
    component: TaskHandlerDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'dashboardApp.taskHandler.home.title'
    }
  }
];

export const taskHandlerPopupRoute: Routes = [
  {
    path: 'task-handler-new',
    component: TaskHandlerPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'dashboardApp.taskHandler.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'task-handler/:id/edit',
    component: TaskHandlerPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'dashboardApp.taskHandler.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'task-handler/:id/delete',
    component: TaskHandlerDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'dashboardApp.taskHandler.home.title'
    },
    outlet: 'popup'
  }
];
