package se.deckaddict.android.payquick;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

import android.widget.Toast;
import android.content.Context;
import android.widget.*;

public class XposedHook implements IXposedHookLoadPackage
{
	public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
		// Ensure that we add a hook in the correct package.
		if (!lpparam.packageName.equals("com.seamless.seqr")) {
            return;
		}
		XposedBridge.log("PayQuick::SEQR loaded.");

		findAndHookMethod("com.seamless.mpayment.qr.androidapp.ui.payment.PaymentInvoiceFragment", lpparam.classLoader, "onResume", new XC_MethodHook() {
				// Inject code after the method call.
				@Override
				protected void afterHookedMethod(MethodHookParam param) throws Throwable {

					XposedBridge.log("PayQuick::onResume has been run");

					// What is defined as a static synthetic method in smali code can be reached as an object field.
					Button button = (Button) XposedHelpers.getObjectField(param.thisObject, "j");

					/*
					Prepared to display toast with payment amount.
					Need to fetch amount first though.
					Context context = button.getContext();
					CharSequence text = "SEQR Payment amount: ??";
					int duration = Toast.LENGTH_LONG;
					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
					*/

					XposedBridge.log("PayQuick::After Toast...Before click");

					button.performClick();

					XposedBridge.log("PayQuick::clickPerformed");
					
				}
			});
    }
}

