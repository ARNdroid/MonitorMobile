package br.com.arndroid.monitormobile.provider.systems;

import android.content.UriMatcher;
import android.net.Uri;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.arndroid.monitormobile.provider.Contract;
import br.com.arndroid.monitormobile.provider.OperationParameters;
import br.com.arndroid.monitormobile.utils.UrisUtils;
import br.com.arndroid.monitormobile.provider.BaseProviderOperator;
import br.com.arndroid.monitormobile.provider.Provider;

public class SystemsOperator extends BaseProviderOperator {

    private static final Logger LOG = LoggerFactory.getLogger(SystemsOperator.class);

    // Safe change Systems.Uri: add line for a new uri.
    private static final int SYSTEMS_URI_MATCH = 1;
    private static final int SYSTEMS_ITEM_URI_MATCH = 2;

    public SystemsOperator() {
        UriMatcher matcher =  getUriMatcher();
        // Safe change Systems.Uri: add line for a new uri.
        matcher.addURI(Contract.Systems.CONTENT_URI.getAuthority(),
                UrisUtils.pathForUriMatcherFromUri(Contract.Systems.CONTENT_URI), SYSTEMS_URI_MATCH);
        matcher.addURI(Contract.Systems.CONTENT_URI.getAuthority(),
                UrisUtils.pathForUriMatcherFromUri(Contract.Systems.CONTENT_URI) + "/#",
                SYSTEMS_ITEM_URI_MATCH);
    }

    @Override
    public String getType(Uri uri) {
        switch (getUriMatcher().match(uri)) {
            // Safe change Systems.Uri: add line for a new uri.
            case SYSTEMS_URI_MATCH:
                return Contract.Systems.CONTENT_TYPE;
            case SYSTEMS_ITEM_URI_MATCH:
                return Contract.Systems.CONTENT_ITEM_TYPE;
            default:
                LOG.trace("Unknown uri in getType(Uri):{}", uri);
                return null;
        }
    }

    @Override
    public String tableNameForUri(Uri uri) {
        return Contract.Systems.TABLE_NAME;
    }

    @Override
    public boolean isOperationProhibitedForUri(int operation, Uri uri) {
        int match = getUriMatcher().match(uri);
        if(match == UriMatcher.NO_MATCH) throw new IllegalArgumentException("Unknown uri: " + uri);

        // Safe change Systems.Uri: evaluate line addition for new uri.
        switch (operation) {
            case QUERY_OPERATION:
                return false;
            case INSERT_OPERATION:
                return match != SYSTEMS_URI_MATCH;
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

        // Safe change Systems.Uri: evaluate line(s) addition for new uri.
        if (getUriMatcher().match(uri) == SYSTEMS_ITEM_URI_MATCH) {
            parameters.setSelection(Contract.Systems.ID_SELECTION);
            parameters.setSelectionArgs(new String[] {uri.getLastPathSegment()});
        }
    }
}