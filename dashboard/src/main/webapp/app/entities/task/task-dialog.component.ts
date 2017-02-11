import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Task } from './task.model';
import { TaskPopupService } from './task-popup.service';
import { TaskService } from './task.service';
import { Node, NodeService } from '../node';
import { TaskHandler, TaskHandlerService } from '../task-handler';
import { EventHandler, EventHandlerService } from '../event-handler';
@Component({
    selector: 'jhi-task-dialog',
    templateUrl: './task-dialog.component.html'
})
export class TaskDialogComponent implements OnInit {

    task: Task;
    authorities: any[];
    isSaving: boolean;

    nodes: Node[];

    taskhandlers: TaskHandler[];

    eventhandlers: EventHandler[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private taskService: TaskService,
        private nodeService: NodeService,
        private taskHandlerService: TaskHandlerService,
        private eventHandlerService: EventHandlerService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['task']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.nodeService.query({filter: 'task-is-null'}).subscribe((res: Response) => {
            if (!this.task.node || !this.task.node.id) {
                this.nodes = res.json();
            } else {
                this.nodeService.find(this.task.node.id).subscribe((subRes: Response) => {
                    this.nodes = [subRes].concat(res.json());
                }, (subRes: Response) => this.onError(subRes.json()));
            }
        }, (res: Response) => this.onError(res.json()));
        this.taskHandlerService.query({filter: 'task-is-null'}).subscribe((res: Response) => {
            if (!this.task.taskHandler || !this.task.taskHandler.id) {
                this.taskhandlers = res.json();
            } else {
                this.taskHandlerService.find(this.task.taskHandler.id).subscribe((subRes: Response) => {
                    this.taskhandlers = [subRes].concat(res.json());
                }, (subRes: Response) => this.onError(subRes.json()));
            }
        }, (res: Response) => this.onError(res.json()));
        this.eventHandlerService.query({filter: 'task-is-null'}).subscribe((res: Response) => {
            if (!this.task.eventHandler || !this.task.eventHandler.id) {
                this.eventhandlers = res.json();
            } else {
                this.eventHandlerService.find(this.task.eventHandler.id).subscribe((subRes: Response) => {
                    this.eventhandlers = [subRes].concat(res.json());
                }, (subRes: Response) => this.onError(subRes.json()));
            }
        }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.task.id !== undefined) {
            this.taskService.update(this.task)
                .subscribe((res: Task) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.taskService.create(this.task)
                .subscribe((res: Task) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Task) {
        this.eventManager.broadcast({ name: 'taskListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError (error) {
        this.isSaving = false;
        this.onError(error);
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }

    trackNodeById(index: number, item: Node) {
        return item.id;
    }

    trackTaskHandlerById(index: number, item: TaskHandler) {
        return item.id;
    }

    trackEventHandlerById(index: number, item: EventHandler) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-task-popup',
    template: ''
})
export class TaskPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private taskPopupService: TaskPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.taskPopupService
                    .open(TaskDialogComponent, params['id']);
            } else {
                this.modalRef = this.taskPopupService
                    .open(TaskDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
