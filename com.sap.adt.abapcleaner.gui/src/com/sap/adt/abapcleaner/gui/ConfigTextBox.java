package com.sap.adt.abapcleaner.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.sap.adt.abapcleaner.base.StringUtil;
import com.sap.adt.abapcleaner.rulebase.*;

class ConfigTextBox extends ConfigControl {
	// even if the configuration allows for a longer text, the width of the control should not exceed 100 chars
	private final int MAX_WIDTH_IN_CHARS = 100;  
	
   private Label lblDescription;
   private Text txtValue;
   private Button btnAction;
   private Label lblEmpty; // only used if no btnAction is used

   @Override
   public Control[] getControls() {
   	if (btnAction != null) {
   		return new Control[] { lblDescription, txtValue, btnAction };
   	} else {
   		return new Control[] { lblDescription, txtValue, lblEmpty };
   	}
   }
   @Override
	public Control[] getControlsForHighlight() { 
   	return new Control[] { lblDescription };
  	}
   private ConfigTextValue getConfigTextValue() {
      return (ConfigTextValue)configValue;
   }

   ConfigTextBox(ConfigTextValue configValue, IConfigDisplay configDisplay, Composite parent) {
      super(configValue, configDisplay);

      lblDescription = new Label(parent, SWT.NONE);
      lblDescription.setText(configValue.description);

      txtValue = new Text(parent, SWT.BORDER);
      txtValue.setText(configValue.getValue());
      txtValue.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
		      getConfigTextValue().setValue(txtValue.getText());
		      configDisplay.configurationChanged();
			}
		});

      // set the width hint depending on the width of the maximum value
      GC gc = new GC(txtValue);
      gc.setFont(txtValue.getFont());
      boolean grabExcessHorizontalSpace = (configValue.visibleLengthHint >= MAX_WIDTH_IN_CHARS);
      GridData gridData = new GridData((grabExcessHorizontalSpace ? SWT.FILL : SWT.BEGINNING), SWT.CENTER, grabExcessHorizontalSpace, false);
      int maxWidthInChars = Math.min(configValue.visibleLengthHint, MAX_WIDTH_IN_CHARS);
      gridData.widthHint = (int) (gc.getFontMetrics().getAverageCharacterWidth() * maxWidthInChars);
      txtValue.setLayoutData(gridData);
      gc.dispose();

      if (StringUtil.isNullOrEmpty(configValue.buttonText)) {
         lblEmpty = new Label(parent, SWT.NONE);
      } else {
			btnAction = new Button(parent, SWT.NONE);
			btnAction.setText(configValue.buttonText);
			btnAction.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					configDisplay.buttonClicked(configValue);
				}
			});
      }
   }

   @Override
   protected void detachControls() {
      if (lblDescription != null)
         lblDescription = null;

      if (txtValue != null) {
      	removeListeners(txtValue, SWT.Modify);
         txtValue = null;
      }
      
      if (btnAction != null) {
      	removeListeners(btnAction, SWT.Selection);
      	btnAction = null;
      }

      if (lblEmpty != null) {
      	lblEmpty = null;
      }
   }

   @Override
   public void setDefault() {
      if (txtValue != null) {
         txtValue.setText(getConfigTextValue().defaultValue);
      }
   }

   @Override
   public void setEnabled(boolean writable, boolean enabled) {
   	txtValue.setEnabled(writable && enabled);
   }
}
