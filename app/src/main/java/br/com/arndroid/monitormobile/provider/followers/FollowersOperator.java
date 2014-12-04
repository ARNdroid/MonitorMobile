package br.com.arndroid.monitormobile.provider.followers;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.net.Uri;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.arndroid.monitormobile.provider.BaseProviderOperator;
import br.com.arndroid.monitormobile.provider.Contract;
import br.com.arndroid.monitormobile.provider.OperationParameters;
import br.com.arndroid.monitormobile.provider.Provider;
import br.com.arndroid.monitormobile.utils.UrisUtils;

public class FollowersOperator extends BaseProviderOperator {

    private static final Logger LOG = LoggerFactory.getLogger(FollowersOperator.class);

    // Safe change Followers.Uri: add line for a new uri.
    private static final int FOLLOWERS_URI_MATCH = 1;
    private static final int FOLLOWERS_ITEM_URI_MATCH = 2;

    public FollowersOperator() {
        UriMatcher matcher =  getUriMatcher();
        // Safe change Followers.Uri: add line for a new uri.
        matcher.addURI(Contract.Followers.CONTENT_URI.getAuthority(),
                UrisUtils.pathForUriMatcherFromUri(Contract.Followers.CONTENT_URI), FOLLOWERS_URI_MATCH);
        matcher.addURI(Contract.Followers.CONTENT_URI.getAuthority(),
                UrisUtils.pathForUriMatcherFromUri(Contract.Followers.CONTENT_URI) + "/#",
                FOLLOWERS_ITEM_URI_MATCH);
    }

    @Override
    public String getType(Uri uri) {
        switch (getUriMatcher().match(uri)) {
            // Safe change Followers.Uri: add line for a new uri.
            case FOLLOWERS_URI_MATCH:
                return Contract.Followers.CONTENT_TYPE;
            case FOLLOWERS_ITEM_URI_MATCH:
                return Contract.Followers.CONTENT_ITEM_TYPE;
            default:
                LOG.trace("Unknown uri in getType(Uri):{}", uri);
                return null;
        }
    }

    @Override
    public String tableNameForUri(Uri uri) {
        return Contract.Followers.TABLE_NAME;
    }

    @Override
    public boolean isOperationProhibitedForUri(int operation, Uri uri) {
        int match = getUriMatcher().match(uri);
        if(match == UriMatcher.NO_MATCH) throw new IllegalArgumentException("Unknown uri: " + uri);

        // Safe change Followers.Uri: evaluate line addition for new uri.
        switch (operation) {
            case QUERY_OPERATION:
                return false;
            case INSERT_OPERATION:
                return match != FOLLOWERS_URI_MATCH;
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

        // Safe change Followers.Uri: evaluate line(s) addition for new uri.
        if (getUriMatcher().match(uri) == FOLLOWERS_ITEM_URI_MATCH) {
            parameters.setSelection(Contract.Followers.ID_SELECTION);
            parameters.setSelectionArgs(new String[] {uri.getLastPathSegment()});
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values, Provider provider) {
        final Uri resultUri = super.insert(uri, values, provider);

        if(resultUri != null) {
            // It could be better. We could notify the correct issue id. Nevertheless it's ok for now...
            final Uri extraUri = Contract.Issues.CONTENT_URI;
            final ContentResolver resolver = provider.getContext().getContentResolver();
            LOG.trace("insert about to notify extraUri={}", extraUri);
            resolver.notifyChange(extraUri, null);
            LOG.trace("insert notified extraUri={}", extraUri);
        }

        return resultUri;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs, Provider provider) {
        final int result = super.update(uri, values, selection, selectionArgs, provider);

        if(result > FAIL) {
            // It could be better. We could notify the correct issue id. Nevertheless it's ok for now...
            final Uri extraUri = Contract.Issues.CONTENT_URI;
            final ContentResolver resolver = provider.getContext().getContentResolver();
            LOG.trace("insert about to notify extraUri={}", extraUri);
            resolver.notifyChange(extraUri, null);
            LOG.trace("insert notified extraUri={}", extraUri);
        }

        return result;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs, Provider provider) {
        final int result = super.delete(uri, selection, selectionArgs, provider);

        if(result > FAIL) {
            // It could be better. We could notify the correct issue id. Nevertheless it's ok for now...
            final Uri extraUri = Contract.Issues.CONTENT_URI;
            final ContentResolver resolver = provider.getContext().getContentResolver();
            LOG.trace("insert about to notify extraUri={}", extraUri);
            resolver.notifyChange(extraUri, null);
            LOG.trace("insert notified extraUri={}", extraUri);
        }

        return result;
    }
}