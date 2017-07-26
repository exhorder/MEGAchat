/*
 * (c) 2013-2015 by Mega Limited, Auckland, New Zealand
 *
 * This file is part of the MEGA SDK - Client Access Engine.
 *
 * Applications using the MEGA API must present a valid application key
 * and comply with the the rules set forth in the Terms of Service.
 *
 * The MEGA SDK is distributed in the hope that it will be useful,\
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * @copyright Simplified (2-clause) BSD License.
 * You should have received a copy of the license along with this
 * program.
 */
package nz.mega.sdk;

/**
 * Interface to receive information about requests.
 * <p>
 * All requests allows to pass a pointer to an implementation of this interface in the last parameter.
 * You can also get information about all requests using MegaApi.addRequestListener().
 * MegaListener objects can also receive information about requests.
 * This interface uses MegaRequest objects to provide information about requests.
 * Please note that not all fields of MegaRequest objects are valid for all requests.
 * See the documentation about each request to know which fields contain useful information for each one.
 *
 * @see MegaChatRequestListenerInterface
 * @see MegaChatRequestListener
 */
class DelegateMegaChatRequestListener extends MegaChatRequestListener {

    MegaChatApiJava megaChatApi;
    MegaChatRequestListenerInterface listener;
    boolean singleListener;

    DelegateMegaChatRequestListener(MegaChatApiJava megaApi, MegaChatRequestListenerInterface listener, boolean singleListener) {
        this.megaChatApi = megaApi;
        this.listener = listener;
        this.singleListener = singleListener;
    }

    MegaChatRequestListenerInterface getUserListener() {
        return listener;
    }

    /**
     * This function is called when a request is about to start being processed.
     * <p>
     * The SDK retains the ownership of the request parameter. Do not use it after this functions returns.
     * The api object is the one created by the application, it will be valid until the application deletes it.
     *
     * @param api
     *            API that started the request.
     * @param request
     *            Information about the request.
     * @see MegaRequestListenerInterface#onRequestStart(MegaApiJava api, MegaRequest request)
     * @see MegaRequestListener#onRequestStart(MegaApi api, MegaRequest request)
     */
    @Override
    public void onRequestStart(MegaChatApi api, MegaChatRequest request) {
        if (listener != null) {
            final MegaChatRequest megaRequest = request.copy();
            megaChatApi.runCallback(new Runnable() {
                public void run() {
                    listener.onRequestStart(megaChatApi, megaRequest);
                }
            });
        }
    }

    /**
     * This function is called to get details about the progress of a request.
     * <p>
     * Currently, this callback is only used for fetchNodes requests (MegaRequest.TYPE_FETCH_NODES).
     * The SDK retains the ownership of the request parameter. Do not use it after this function returns.
     * The api object is the one created by the application, it will be valid until the application deletes it.
     *
     * @param api
     *            API that started the request.
     * @param request
     *            Information about the request.
     * @see MegaRequestListenerInterface#onRequestUpdate(MegaApiJava api, MegaRequest request)
     * @see MegaRequestListener#onRequestUpdate(MegaApi api, MegaRequest request)
     */
    @Override
    public void onRequestUpdate(MegaChatApi api, MegaChatRequest request) {
        if (listener != null) {
            final MegaChatRequest megaRequest = request.copy();
            megaChatApi.runCallback(new Runnable() {
                public void run() {
                    listener.onRequestUpdate(megaChatApi, megaRequest);
                }
            });
        }
    }

    /**
     * This function is called when a request has finished.
     * <p>
     * There will be no further callbacks related to this request.
     * The MegaError parameter provides the result of the request.
     * If the request completed without problems, the error code will be API_OK. The SDK retains the ownership
     * of the request and error parameters. Do not use them after this functions returns.
     * The api object is the one created by the application, it will be valid until the application deletes it.
     *
     * @param api
     *            API that started the request.
     * @param request
     *            Information about the request.
     * @param e
     *            Error Information.
     * @see MegaRequestListenerInterface#onRequestFinish(MegaApiJava api, MegaRequest request, MegaError e)
     * @see MegaRequestListener#onRequestFinish(MegaApi api, MegaRequest request, MegaError e)
     */
    @Override
    public void onRequestFinish(MegaChatApi api, MegaChatRequest request, MegaChatError e) {
        if (listener != null) {
            final MegaChatRequest megaRequest = request.copy();
            final MegaChatError megaError = e.copy();
            megaChatApi.runCallback(new Runnable() {
                public void run() {
                    listener.onRequestFinish(megaChatApi, megaRequest, megaError);
                    
                    if (singleListener) {
                        megaChatApi.privateFreeRequestListener(DelegateMegaChatRequestListener.this);
                    }
                }
            });
        }
    }

    /**
     * This function is called when there is a temporary error processing a request.
     * <p>
     * The request continues after this callback, so expect more MegaRequestListener.onRequestTemporaryError
     * or a MegaRequestListener.onRequestFinish callback. The SDK retains the ownership of the request and
     * error parameters. Do not use them after this functions returns.
     * The api object is the one created by the application, it will be valid until the application deletes it.
     *
     * @param api
     *            API that started the request.
     * @param request
     *            Information about the request.
     * @param e
     *            Error Information.
     * @see MegaRequestListenerInterface#onRequestTemporaryError(MegaApiJava api, MegaRequest request, MegaError e)
     * @see MegaRequestListener#onRequestTemporaryError(MegaApi api, MegaRequest request, MegaError error)
     */
    @Override
    public void onRequestTemporaryError(MegaChatApi api, MegaChatRequest request, MegaChatError e) {
        if (listener != null) {
            final MegaChatRequest megaRequest = request.copy();
            final MegaChatError megaError = e.copy();
            megaChatApi.runCallback(new Runnable() {
                public void run() {
                    listener.onRequestTemporaryError(megaChatApi, megaRequest, megaError);
                }
            });
        }
    }
}
