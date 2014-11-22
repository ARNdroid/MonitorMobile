package br.gov.caixa.monitormobile.provider.systems;

import android.content.UriMatcher;
import android.net.Uri;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.gov.caixa.monitormobile.provider.BaseProviderOperator;
import br.gov.caixa.monitormobile.provider.Contract;
import br.gov.caixa.monitormobile.provider.OperationParameters;
import br.gov.caixa.monitormobile.provider.Provider;
import br.gov.caixa.monitormobile.utils.UrisUtils;

public class SystemsOperator extends BaseProviderOperator {

    private static final Logger LOG = LoggerFactory.getLogger(SystemsOperator.class);

    // Safe change Users.Uri: add line for a new uri.
    private static final int USERS_URI_MATCH = 1;
    private static final int USERS_ITEM_URI_MATCH = 2;

    public SystemsOperator() {
        UriMatcher matcher =  getUriMatcher();
        // Safe change Users.Uri: add line for a new uri.
        matcher.addURI(Contract.Systems.CONTENT_URI.getAuthority(),
                UrisUtils.pathForUriMatcherFromUri(Contract.Systems.CONTENT_URI), USERS_URI_MATCH);
        matcher.addURI(Contract.Systems.CONTENT_URI.getAuthority(),
                UrisUtils.pathForUriMatcherFromUri(Contract.Systems.CONTENT_URI) + "/#",
                USERS_ITEM_URI_MATCH);
    }

    @Override
    public String getType(Uri uri) {
        switch (getUriMatcher().match(uri)) {
            // Safe change Users.Uri: add line for a new uri.
            case USERS_URI_MATCH:
                return Contract.Systems.CONTENT_TYPE;
            case USERS_ITEM_URI_MATCH:
                return Contract.Systems.CONTENT_ITEM_TYPE;
            default:
                LOG.trace("Unknown uri in getType(Uri):{}", uri);
                return null;
        }
    }

    @Override
    public String tableName() {
        return Contract.Systems.TABLE_NAME;
    }

    @Override
    public boolean isOperationProhibitedForUri(int operation, Uri uri) {
        int match = getUriMatcher().match(uri);
        if(match == UriMatcher.NO_MATCH) throw new IllegalArgumentException("Unknown uri: " + uri);

        // Safe change Users.Uri: evaluate line addition for new uri.
        switch (operation) {
            case QUERY_OPERATION:
                return false;
            case INSERT_OPERATION:
                return match != USERS_URI_MATCH;
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

        // Safe change Users.Uri: evaluate line(s) addition for new uri.
        if (getUriMatcher().match(uri) == USERS_ITEM_URI_MATCH) {
            parameters.setSelection(Contract.Systems.ID_SELECTION);
            parameters.setSelectionArgs(new String[] {uri.getLastPathSegment()});
        }
    }
}