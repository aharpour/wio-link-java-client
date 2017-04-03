const enum Language {
    'GROOVYSCRIPT',
    'JAVASCRIPT',
    'JAVA'
};
export class TaskHandler {
    constructor(
        public id?: number,
        public name?: string,
        public language?: Language,
        public code?: any,
    ) { }
}
