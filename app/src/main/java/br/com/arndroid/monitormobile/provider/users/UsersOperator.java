package br.com.arndroid.monitormobile.provider.users;

import android.content.UriMatcher;
import android.net.Uri;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.arndroid.monitormobile.provider.Contract;
import br.com.arndroid.monitormobile.provider.OperationParameters;
import br.com.arndroid.monitormobile.provider.Provider;
import br.com.arndroid.monitormobile.utils.UrisUtils;
import br.com.arndroid.monitormobile.provider.BaseProviderOperator;

public class UsersOperator extends BaseProviderOperator {

    private static final Logger LOG = LoggerFactory.getLogger(UsersOperator.class);

    // Safe change Users.Uri: add line for a new uri.
    private static final int USERS_URI_MATCH = 1;
    private static final int USERS_ITEM_URI_MATCH = 2;

    public UsersOperator() {
        UriMatcher matcher =  getUriMatcher();
        // Safe change Users.Uri: add line for a new uri.
        matcher.addURI(Contract.Users.CONTENT_URI.getAuthority(),
                UrisUtils.pathForUriMatcherFromUri(Contract.Users.CONTENT_URI), USERS_URI_MATCH);
        matcher.addURI(Contract.Users.CONTENT_URI.getAuthority(),
                UrisUtils.pathForUriMatcherFromUri(Contract.Users.CONTENT_URI) + "/#",
                USERS_ITEM_URI_MATCH);
    }

    @Override
    public String getType(Uri uri) {
        switch (getUriMatcher().match(uri)) {
            // Safe change Users.Uri: add line for a new uri.
            case USERS_URI_MATCH:
                return Contract.Users.CONTENT_TYPE;
            case USERS_ITEM_URI_MATCH:
                return Contract.Users.CONTENT_ITEM_TYPE;
            default:
                LOG.trace("Unknown uri in getType(Uri):{}", uri);
                return null;
        }
    }

    @Override
    public String tableNameForUri(Uri uri) {
        return Contract.Users.TABLE_NAME;
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
            parameters.setSelection(Contract.Users.ID_SELECTION);
            parameters.setSelectionArgs(new String[] {uri.getLastPathSegment()});
        }
    }
}