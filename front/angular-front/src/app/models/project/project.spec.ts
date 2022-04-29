import { Project } from './project';
import {ProjectId} from "./id/project-id";
import {ProjectAlias} from "./alias/project-alias";
import {ProjectName} from "./name/project-name";
import {ProjectOverview} from "./overview/project-overview";

describe('Project', () => {
  it('should create an instance', () => {
    const projectId = new ProjectId('abcde-fghij-klmno-pqrst-uvwxy-z12345');
    const projectAlias = new ProjectAlias('ALIAS');
    const projectName = new ProjectName('プロジェクト名称');
    const projectOverview = new ProjectOverview('プロジェクト概要');
    expect(new Project(projectId, projectAlias, projectName, projectOverview)).toBeTruthy();
  });
});
