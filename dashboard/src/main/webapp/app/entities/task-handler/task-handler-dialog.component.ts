import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService, DataUtils } from 'ng-jhipster';

import { TaskHandler, Language } from './task-handler.model';
import { TaskHandlerPopupService } from './task-handler-popup.service';
import { TaskHandlerService } from './task-handler.service';
import { CodemirrorComponent } from 'ng2-codemirror';
@Component({
    selector: 'jhi-task-handler-dialog',
    templateUrl: './task-handler-dialog.component.html'
})
export class TaskHandlerDialogComponent implements OnInit {
    @ViewChild('editor') editor: CodemirrorComponent;
    editorConfig;
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
        this.jhiLanguageService.setLocations(['taskHandler', 'language']);
    }

    ngOnInit() {
        this.editorConfig = {
            lineNumbers: true,
            mode: 'javascript'
        };
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    languageChanged() {

        switch(this.taskHandler.language.toString()) {
            case 'JAVA': {
                this.taskHandler.code = "";
                break;
            }
            case 'JAVASCRIPT': {
                this.taskHandler.code = `var constructor = function (period, log) {
    return {
        imports: new JavaImporter(java.util, Packages.nl.openweb.iot.wio.domain.grove, Packages.nl.openweb.iot.wio.scheduling),
        log: log,
        period: period,
        execute: function (node, context) {
            with(this.imports) {
                //var temp = node.getGroveByType(GroveTempHum.class).get().readTemperature();
                //print(temp);
                return SchedulingUtils.secondsLater(this.period)
            }
        }
    }
}`;
                break;
            }
            case 'GROOVYSCRIPT': {
                this.taskHandler.code = `package groovy.scripts

import java.lang.*
import java.util.*
import java.text.*
import java.time.*
import nl.openweb.iot.dashboard.service.script.AbstractGroovyTaskHandler
import nl.openweb.iot.wio.WioException
import nl.openweb.iot.wio.domain.*
import nl.openweb.iot.wio.domain.grove.*
import nl.openweb.iot.wio.scheduling.*

class TaskHandler extends AbstractGroovyTaskHandler {
    @Override
    ScheduledTask.TaskExecutionResult execute(Node node, TaskContext context) throws WioException {
        ScheduledTask.TaskExecutionResult result = SchedulingUtils.secondsLater((int) Math.round(period * 60))
        // double luminance = node.getGroveByType(GroveLuminance.class).get().readLuminance()
        // System.out.println(luminance)
        return result
    }
}`;
                break;
            }

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
