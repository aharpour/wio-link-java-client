import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService, DataUtils } from 'ng-jhipster';

import { TaskHandler } from './task-handler.model';
import { TaskHandlerPopupService } from './task-handler-popup.service';
import { TaskHandlerService } from './task-handler.service';
@Component({
    selector: 'jhi-task-handler-dialog',
    templateUrl: './task-handler-dialog.component.html'
})
export class TaskHandlerDialogComponent implements OnInit {

    taskHandler: TaskHandler;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private dataUtils: DataUtils,
        private alertService: AlertService,
        private taskHandlerService: TaskHandlerService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['taskHandler', 'langauge']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData($event, taskHandler, field, isImage) {
        if ($event.target.files && $event.target.files[0]) {
            let $file = $event.target.files[0];
            if (isImage && !/^image\//.test($file.type)) {
                return;
            }
            this.dataUtils.toBase64($file, (base64Data) => {
                taskHandler[field] = base64Data;
                taskHandler[`${field}ContentType`] = $file.type;
            });
        }
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.taskHandler.id !== undefined) {
            this.taskHandlerService.update(this.taskHandler)
                .subscribe((res: TaskHandler) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.taskHandlerService.create(this.taskHandler)
                .subscribe((res: TaskHandler) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: TaskHandler) {
        this.eventManager.broadcast({ name: 'taskHandlerListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-task-handler-popup',
    template: ''
})
export class TaskHandlerPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private taskHandlerPopupService: TaskHandlerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.taskHandlerPopupService
                    .open(TaskHandlerDialogComponent, params['id']);
            } else {
                this.modalRef = this.taskHandlerPopupService
                    .open(TaskHandlerDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
