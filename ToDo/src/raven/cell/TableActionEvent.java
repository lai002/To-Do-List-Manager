/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raven.cell;

/**
 *
 * @author Admin
 */
public interface TableActionEvent {
    public void OnEdit(int row);
    public void OnRemove(int row);
}
