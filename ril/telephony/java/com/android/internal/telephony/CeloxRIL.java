/*
 * Copyright (C) 2014 The CyanogenMod Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.internal.telephony;

import static com.android.internal.telephony.RILConstants.*;

import android.content.Context;
import android.os.Parcel;

/**
 * RIL for devices in the Samsung Celox family.
 * This class contains workarounds for various things.
 * {@hide}
 */
public class CeloxRIL extends SamsungQualcommRIL {

    public CeloxRIL(Context context, int networkMode, int cdmaSubscription) {
        super(context, networkMode, cdmaSubscription);
    }

    @Override
    protected void
    processUnsolicited(Parcel p) {
        int dataPosition = p.dataPosition();
        int origResponse = p.readInt();
        int newResponse = origResponse;
        switch (origResponse) {
            case 1036:
                newResponse = RIL_UNSOL_RESPONSE_IMS_NETWORK_STATE_CHANGED;
                break;
            case 1039:
                newResponse = RIL_UNSOL_ON_SS;
                break;
            case 1040:
                newResponse = RIL_UNSOL_STK_CC_ALPHA_NOTIFY;
                break;
            case 1041:
                newResponse = RIL_UNSOL_UICC_SUBSCRIPTION_STATUS_CHANGED;
                break;
            case 1037: // RIL_UNSOL_TETHERED_MODE_STATE_CHANGED
            case 1038: // RIL_UNSOL_DATA_NETWORK_STATE_CHANGED
            case 1042: // RIL_UNSOL_QOS_STATE_CHANGED_IND
                riljLog("CeloxRIL: ignoring response " + origResponse +
                        " due to celoxRemap");
                return;
        }
        if (newResponse != origResponse) {
            riljLog("CeloxRIL: remap from " + origResponse +
                    " to " + newResponse);
            p.setDataPosition(dataPosition);
            p.writeInt(newResponse);
        }
        p.setDataPosition(dataPosition);
        super.processUnsolicited(p);
    }

}
