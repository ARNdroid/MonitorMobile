package br.gov.caixa.monitormobile.provider.xs;

import android.content.UriMatcher;
import android.net.Uri;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.gov.caixa.monitormobile.provider.BaseProviderOperator;
import br.gov.caixa.monitormobile.provider.Contract;
import br.gov.caixa.monitormobile.provider.OperationParameters;
import br.gov.caixa.monitormobile.provider.Provider;
import br.gov.caixa.monitormobile.utils.UrisUtils;

public class XsOperator extends BaseProviderOperator {

    private static final Logger LOG = LoggerFactory.getLogger(XsOperator.class);

    // Safe change Xs.Uri: add line for a new uri.
    private static final int XS_URI_MATCH = 1;
    private static final int XS_ITEM_URI_MATCH = 2;

    public XsOperator() {
        UriMatcher matcher =  getUriMatcher();
        // Safe change Xs.Uri: add line for a new uri.
        matcher.addURI(Contract.Xs.CONTENT_URI.getAuthority(),
                UrisUtils.pathForUriMatcherFromUri(Contract.Xs.CONTENT_URI), XS_URI_MATCH);
        matcher.addURI(Contract.Xs.CONTENT_URI.getAuthority(),
                UrisUtils.pathForUriMatcherFromUri(Contract.Xs.CONTENT_URI) + "/#",
                XS_ITEM_URI_MATCH);
    }

    @Override
    public String getType(Uri uri) {
        switch (getUriMatcher().match(uri)) {
            // Safe change Xs.Uri: add line for a new uri.
            case XS_URI_MATCH:
                return Contract.Xs.CONTENT_TYPE;
            case XS_ITEM_URI_MATCH:
                return Contract.Xs.CONTENT_ITEM_TYPE;
            default:
                LOG.trace("Unknown uri in getType(Uri):{}", uri);
                return null;
        }
    }

    @Override
    public String tableName() {
        return Contract.Xs.TABLE_NAME;
    }

    @Override
    public boolean isOperationProhibitedForUri(int operation, Uri uri) {
        int match = getUriMatcher().match(uri);
        if(match == UriMatcher.NO_MATCH) throw new IllegalArgumentException("Unknown uri: " + uri);

        // Safe change Xs.Uri: evaluate line addition for new uri.
        switch (operation) {
            case QUERY_OPERATION:
                return false;
            case INSERT_OPERATION:
                return match != XS_URI_MATCH;
            case UPDATE_OPERATION:
                return false;
            case DELETE_OPERATION:
                return false;
            default:
                throw new IllegalArgumentException("Unknown operation: " + operation);
        }
    }

    @Override
    public void onValidateParameters(int operation, Uri uri, OperationParameters parameters,
                                     Provider provider) {

        // Safe change Xs.Uri: evaluate line(s) addition for new uri.
        if (getUriMatcher().match(uri) == XS_ITEM_URI_MATCH) {
            parameters.setSelection(Contract.Xs.ID_SELECTION);
            parameters.setSelectionArgs(new String[] {uri.getLastPathSegment()});
        }
    }
}