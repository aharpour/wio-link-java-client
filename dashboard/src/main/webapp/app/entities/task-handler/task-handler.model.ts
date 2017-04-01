const enum Langauge {
    'GROOVYSCRIPT',
    'JAVASCRIPT',
    'JAVA'
};
export class TaskHandler {
    constructor(
        public id?: number,
        public name?: string,
        public langauge?: Langauge,
        public code?: any,
    ) { }
}
