import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { TaskComponent } from './task.component';
import { TaskDetailComponent } from './task-detail.component';
import { TaskPopupComponent } from './task-dialog.component';
import { TaskDeletePopupComponent } from './task-delete-dialog.component';

import { Principal } from '../../shared';


export const taskRoute: Routes = [
  {
    path: 'task',
    component: TaskComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'dashboardApp.task.home.title'
    }
  }, {
    path: 'task/:id',
    component: TaskDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'dashboardApp.task.home.title'
    }
  }
];

export const taskPopupRoute: Routes = [
  {
    path: 'task-new',
    component: TaskPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'dashboardApp.task.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'task/:id/edit',
    component: TaskPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'dashboardApp.task.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'task/:id/delete',
    component: TaskDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'dashboardApp.task.home.title'
    },
    outlet: 'popup'
  }
];
