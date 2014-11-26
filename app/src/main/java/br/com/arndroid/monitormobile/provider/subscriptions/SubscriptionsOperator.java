package br.com.arndroid.monitormobile.provider.subscriptions;

import android.content.UriMatcher;
import android.net.Uri;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.arndroid.monitormobile.provider.BaseProviderOperator;
import br.com.arndroid.monitormobile.provider.Contract;
import br.com.arndroid.monitormobile.provider.OperationParameters;
import br.com.arndroid.monitormobile.provider.Provider;
import br.com.arndroid.monitormobile.utils.UrisUtils;

public class SubscriptionsOperator extends BaseProviderOperator {

    private static final Logger LOG = LoggerFactory.getLogger(SubscriptionsOperator.class);

    // Safe change Subscriptions.Uri: add line for a new uri.
    private static final int SUBSCRIPTIONS_URI_MATCH = 1;
    private static final int SUBSCRIPTIONS_ITEM_URI_MATCH = 2;

    public SubscriptionsOperator() {
        UriMatcher matcher =  getUriMatcher();
        // Safe change Subscriptions.Uri: add line for a new uri.
        matcher.addURI(Contract.Subscriptions.CONTENT_URI.getAuthority(),
                UrisUtils.pathForUriMatcherFromUri(Contract.Subscriptions.CONTENT_URI), SUBSCRIPTIONS_URI_MATCH);
        matcher.addURI(Contract.Subscriptions.CONTENT_URI.getAuthority(),
                UrisUtils.pathForUriMatcherFromUri(Contract.Subscriptions.CONTENT_URI) + "/#",
                SUBSCRIPTIONS_ITEM_URI_MATCH);
    }

    @Override
    public String getType(Uri uri) {
        switch (getUriMatcher().match(uri)) {
            // Safe change Subscriptions.Uri: add line for a new uri.
            case SUBSCRIPTIONS_URI_MATCH:
                return Contract.Subscriptions.CONTENT_TYPE;
            case SUBSCRIPTIONS_ITEM_URI_MATCH:
                return Contract.Subscriptions.CONTENT_ITEM_TYPE;
            default:
                LOG.trace("Unknown uri in getType(Uri):{}", uri);
                return null;
        }
    }

    @Override
    public String tableName() {
        return Contract.Subscriptions.TABLE_NAME;
    }

    @Override
    public boolean isOperationProhibitedForUri(int operation, Uri uri) {
        int match = getUriMatcher().match(uri);
        if(match == UriMatcher.NO_MATCH) throw new IllegalArgumentException("Unknown uri: " + uri);

        // Safe change Subscriptions.Uri: evaluate line addition for new uri.
        switch (operation) {
            case QUERY_OPERATION:
                return false;
            case INSERT_OPERATION:
                return match != SUBSCRIPTIONS_URI_MATCH;
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

        // Safe change Subscriptions.Uri: evaluate line(s) addition for new uri.
        if (getUriMatcher().match(uri) == SUBSCRIPTIONS_ITEM_URI_MATCH) {
            parameters.setSelection(Contract.Subscriptions.ID_SELECTION);
            parameters.setSelectionArgs(new String[] {uri.getLastPathSegment()});
        }
    }
}